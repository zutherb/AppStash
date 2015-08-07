# $zookeeper, $quorum, $ip and $marathon_zk are passed in
# from the Vagrantfile depending on the cluster set up in cluster.yml

include basenode
class { 'mesos':
  zookeeper => $zookeeper
}
class { 'mesos::master':
  options    => {
    quorum   => $quorum,
    log_dir  => '/var/log/mesos',
    hostname => $ip,
    ip       => $ip,
  },
  zookeeper => $zookeeper,
}
class { 'zookeeper':
  id => $zookeeper_id,
}
class { 'marathon':
  marathon_zk => $marathon_zk
} ->
docker::run { 'vamp':
  image           => 'magneticio/vamp-mesosphere:latest',
  ports           => ['81:80', '9200:9200', '8081:8080', '10002:10001', '8084:8083'],
  env             => [
    'VAMP_MARATHON_URL=http://172.31.1.11:8080',
    'VAMP_ROUTER_HOST=172.31.2.11',
    'VAMP_ROUTER_URL=http://172.31.2.11:10001'
  ],
  restart_service => true,
  pull_on_start   => true,
}

# Ensure Slave service not running on master nodes.
service { 'mesos-slave':
  ensure => stopped,
  enable => false,
  require => Package['mesos']
}

if $consul_enable {
  class { 'consul':
    join_cluster => $join_cluster,
    config_hash => {
      'server'           => true,
      'bootstrap_expect' => $bootstrap_expect,
      'data_dir'         => '/opt/consul',
      'ui_dir'           => '/opt/consul/ui',
      'log_level'        => 'INFO',
      'client_addr'      => '0.0.0.0',
      'advertise_addr'   => $ip,
      'node_name'        => $node_name,
      'datacenter'       => $datacenter,
    }
  }
}
