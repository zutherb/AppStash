# $zookeeper, $ip are passed in from the Vagrantfile depending on the cluster
# set up in cluster.yml

include basenode
class { 'mesos':
  zookeeper => $zookeeper
}

class { 'mesos::slave':
  zookeeper => $zookeeper,
  options => {
    containerizers                => 'docker,mesos',
    executor_registration_timeout => '5mins',
    hostname                      => $ip,
    ip                            => $ip,
    log_dir                       => '/var/log/mesos'
  }
} ->
docker::image { [
    "zutherb/monolithic-shop",
    "zutherb/catalog-frontend",
    "zutherb/catalog-frontend:latest-b",
    "zutherb/product-service",
    "zutherb/navigation-service",
    "zutherb/cart-service",
    "magneticio/navigation-service",
    "magneticio/vamp-router",
    "redis",
    "mongo",
  ]:
}


# Ensure we are not running zk, mesos-master or marathon on slave nodes.
service { 'mesos-master':
  ensure => stopped,
  enable => false,
  require => Package['mesos']
}
service { 'zookeeper':
  ensure  => stopped,
  enable  => false,
  require => Package['mesos']
}

if $consul_enable {
  class { 'consul':
    join_cluster => $join_cluster,
    config_hash => {
      'data_dir'         => '/opt/consul',
      'log_level'        => 'INFO',
      'client_addr'      => '0.0.0.0',
      'advertise_addr'   => $ip,
      'node_name'        => $node_name,
      'datacenter'       => $datacenter
    }
  }

  # Join the cluster manually. We do this to circumvent the issues described
  # over here -
  # https://github.com/solarkennedy/puppet-consul/pull/42
  # https://github.com/solarkennedy/puppet-consul/issues/31
  exec {'slave join consul cluster':
    cwd         => $consul::config_dir,
    path        => [$consul::bin_dir,'/bin','/usr/bin'],
    command     => "consul join ${join_cluster}",
    subscribe   => Service['consul'],
    require     => Class['consul'],
  }
}