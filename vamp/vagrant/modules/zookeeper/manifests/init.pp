# Class: zookeeper
#
# This module manages zookeeper
#
# Parameters:
#   id
#   user
#   group
#   log_dir
#
# Sample Usage:
#
#   class { 'zookeeper': }
#
class zookeeper(
  $id                      = '1',
  $datastore               = '/var/lib/zookeeper',
  $initialize_datastore    = false,
  # fact from which we get public ip address
  $client_ip               = $::ipaddress,
  $client_port             = 2181,
  $election_port           = 2888,
  $leader_port             = 3888,
  $log_dir                 = '/var/log/zookeeper',
  $cfg_dir                 = '/etc/zookeeper/conf',
  $user                    = 'zookeeper',
  $group                   = 'zookeeper',
  $java_bin                = '/usr/bin/java',
  $java_opts               = '',
  $pid_dir                 = '/var/run/zookeeper',
  $pid_file                = '$PIDDIR/zookeeper.pid',
  $zoo_main                = 'org.apache.zookeeper.server.quorum.QuorumPeerMain',
  $log4j_prop               = 'INFO,ROLLINGFILE',
  $cleanup_sh              = '/usr/share/zookeeper/bin/zkCleanup.sh',
  $servers                 = [],
  $ensure                  = present,
  $snap_count              = 10000,
  # since zookeeper 3.4, for earlier version cron task might be used
  $snap_retain_count       = 3,
  # interval in hours, purging enabled when >= 1
  $purge_interval          = 0,
  # log4j properties
  $rollingfile_threshold   = 'ERROR',
  $tracefile_threshold     = 'TRACE',
  $max_allowed_connections = 10,
  $peer_type               = 'UNSET',
  $start_with              = 'init.d',
  $ensure_cron             = true,
  $service_package         = 'zookeeperd',
  $service_name            = 'zookeeper',
  $packages                = ['zookeeper']
) {

  validate_array($packages)
  validate_bool($ensure_cron)

  anchor { 'zookeeper::start': }->
  class { 'zookeeper::install':
    ensure            => $ensure,
    snap_retain_count => $snap_retain_count,
    datastore         => $datastore,
    user              => $user,
    cleanup_sh        => $cleanup_sh,
    start_with        => $start_with,
    ensure_cron       => $ensure_cron,
    service_package   => $service_package,
    packages          => $packages,
  }->
  class { 'zookeeper::config':
    id                      => $id,
    datastore               => $datastore,
    initialize_datastore    => $initialize_datastore,
    client_ip               => $client_ip,
    client_port             => $client_port,
    election_port           => $election_port,
    leader_port             => $leader_port,
    log_dir                 => $log_dir,
    cfg_dir                 => $cfg_dir,
    user                    => $user,
    group                   => $group,
    java_bin                => $java_bin,
    java_opts               => $java_opts,
    pid_dir                 => $pid_dir,
    zoo_main                => $zoo_main,
    log4j_prop              => $log4j_prop,
    servers                 => $servers,
    snap_count              => $snap_count,
    snap_retain_count       => $snap_retain_count,
    purge_interval          => $purge_interval,
    rollingfile_threshold   => $rollingfile_threshold,
    tracefile_threshold     => $tracefile_threshold,
    max_allowed_connections => $max_allowed_connections,
    peer_type               => $peer_type,
  }->
  class { 'zookeeper::service':
    cfg_dir      => $cfg_dir,
    service_name => $service_name,
  }
  ->
  anchor { 'zookeeper::end': }

}
