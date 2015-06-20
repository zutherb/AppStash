# Class: zookeeper::service

class zookeeper::service(
  $cfg_dir        = '/etc/zookeeper/conf',
  $service_name   = 'zookeeper',
  $service_ensure = 'running',
){
  require zookeeper::install

  service { $service_name:
    ensure     => $service_ensure,
    hasstatus  => true,
    hasrestart => true,
    enable     => true,
    require    => [
      Class['zookeeper::install'],
      File["${cfg_dir}/zoo.cfg"]
    ]
  }
}
