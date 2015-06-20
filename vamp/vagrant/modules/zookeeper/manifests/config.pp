# Class: zookeeper::config
#
# This module manages the zookeeper configuration directories
#
# Parameters:
# [* id *]  zookeeper instance id: between 1 and 255
#
# [* servers *] an Array - specify all zookeeper servers
# The fist port is used by followers to connect to the leader
# The second one is used for leader election
#     server.1=zookeeper1:2888:3888
#     server.2=zookeeper2:2888:3888
#     server.3=zookeeper3:2888:3888
#
#
# Actions: None
#
# Requires: zookeeper::install, zookeeper
#
# Sample Usage: include zookeeper::config
#
class zookeeper::config(
  $id                      = '1',
  $datastore               = '/var/lib/zookeeper',
  $initialize_datastore    = false,
  $client_ip               = $::ipaddress,
  $client_port             = 2181,
  $election_port           = 2888,
  $leader_port             = 3888,
  $snap_count              = 10000,
  $log_dir                 = '/var/log/zookeeper',
  $cfg_dir                 = '/etc/zookeeper/conf',
  $user                    = 'zookeeper',
  $group                   = 'zookeeper',
  $java_bin                = '/usr/bin/java',
  $java_opts               = '',
  $pid_dir                 = '/var/run/zookeeper',
  $pid_file                = '$PIDDIR/zookeeper.pid',
  $zoo_main                = 'org.apache.zookeeper.server.quorum.QuorumPeerMain',
  $log4j_prop              = 'INFO,ROLLINGFILE',
  $servers                 = [''],
  # since zookeeper 3.4, for earlier version cron task might be used
  $snap_retain_count       = 3,
  # interval in hours, purging enabled when >= 1
  $purge_interval          = 0,
  # log4j properties
  $rollingfile_threshold   = 'ERROR',
  $tracefile_threshold     = 'TRACE',
  $max_allowed_connections = 10,
  $export_tag              = 'zookeeper',
  $peer_type               = 'UNSET',
  $tick_time               = 2000,
  $init_limit              = 10,
  $sync_limit              = 5,
  $leader                  = true,
) {
  require zookeeper::install

  file { $cfg_dir:
    ensure  => directory,
    owner   => $user,
    group   => $group,
    recurse => true,
    mode    => '0644',
  }

  file { $log_dir:
    ensure  => directory,
    owner   => $user,
    group   => $group,
    recurse => true,
    mode    => '0644',
  }

  file { $datastore:
    ensure  => directory,
    owner   => $user,
    group   => $group,
    mode    => '0644',
    recurse => true,
  }

  file { "${cfg_dir}/myid":
    ensure  => file,
    content => template('zookeeper/conf/myid.erb'),
    owner   => $user,
    group   => $group,
    mode    => '0644',
    require => File[$cfg_dir],
    notify  => Class['zookeeper::service'],
  }

  file { "${cfg_dir}/zoo.cfg":
    owner   => $user,
    group   => $group,
    mode    => '0644',
    content => template('zookeeper/conf/zoo.cfg.erb'),
    notify  => Class['zookeeper::service'],
  }

  file { "${cfg_dir}/environment":
    owner   => $user,
    group   => $group,
    mode    => '0644',
    content => template('zookeeper/conf/environment.erb'),
    notify  => Class['zookeeper::service'],
  }

  file { "${cfg_dir}/log4j.properties":
    owner   => $user,
    group   => $group,
    mode    => '0644',
    content => template('zookeeper/conf/log4j.properties.erb'),
    notify  => Class['zookeeper::service'],
  }

  # keep track of all hosts in a cluster
  zookeeper::host { $client_ip:
    id            => $id,
    client_ip     => $client_ip,
    election_port => $election_port,
    leader_port   => $leader_port,
  }

  # Initialize the datastore if required
  if $initialize_datastore {
    exec { 'initialize_datastore':
      command => "/usr/bin/zookeeper-server-initialize --myid=${id}",
      user    => $user,
      creates => "${datastore}/myid",
      require => File[$datastore],
    }
  }
}
