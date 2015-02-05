class cassandra {
	include cassandra::add_repos, cassandra::install, cassandra::config, cassandra::service
}



class cassandra::add_repos {
	class { 'apt':
	  always_apt_update    => true,
	  apt_update_frequency => undef,
	  disable_keys         => undef,
	  proxy_host           => false,
	  proxy_port           => '8080',
	  purge_sources_list   => false,
	  purge_sources_list_d => false,
	  purge_preferences_d  => false,
	  update_timeout       => undef,
	  fancy_progress       => undef
	}

	apt_key {
	  'datastaxKey':
	  ensure => 'present',
	  source => 'http://debian.datastax.com/debian/repo_key',
	  id => 'B999A372'
	}

	apt_key {
	  'datastaxKey2':
	  ensure => 'present',
	  source => 'http://debian.datastax.com/debian/repo_key',
	  id => 'B4FE9662'
	}

	apt::source { 
	  'datastaxCassandraRepo':
	  location   => 'http://debian.datastax.com/community',
	  release    => 'stable',
	  repos      => 'main'
	}
	package { "openjdk-7-jdk": ensure => "installed" }
}

class cassandra::install {
	Class["cassandra::add_repos"] -> Class["cassandra::install"]
	package { "dsc21": ensure => "installed" } 
	package { "opscenter": ensure => "installed" }
	#hack-ish - debian packages auto-start service on, cassandra
	#saves default dummy data store name into table, and won't
	#start with the configured - this will hopefully require the user to 
	#delete the directories and try again (add force => true to do it automatically)
	file { "remove_commitlog" :
		ensure => absent,
		path => "/var/lib/cassandra/commitlog",
		force => false
	     }
	file { "remove_data" :
		ensure => absent,
		path => "/var/lib/cassandra/data",
		force => false
	     }
}

class cassandra::config {
	Class["cassandra::install"] -> Class["cassandra::config"]
	file{ "/etc/cassandra/cassandra.yaml":
		ensure => present,
		owner => 'root',
		group => 'root',
		mode => 644,
		source => "puppet:///modules/cassandra/cassandra.yaml"
	}
	file{ "/etc/cassandra/cassandra-rackdc.properties":
		ensure => present,
		owner => 'root',
		group => 'root',
		mode => 644,
		source => "puppet:///modules/cassandra/cassandra-rackdc.properties"
	}
}

class cassandra::service {
	Class["cassandra::config"] -> Class["cassandra::service"]
	service { 'cassandra':
		ensure => running,
		enable => true
	}
	service { 'opscenterd':
		ensure => running,
		enable => true
	}
}
