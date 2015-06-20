#
# ZooKeeper installation on Debian
class zookeeper::os::redhat(
  $ensure            = present,
  $snap_retain_count = 3,
  $cleanup_sh        = '/usr/lib/zookeeper/bin/zkCleanup.sh',
  $datastore         = '/var/lib/zookeeper',
  $user              = 'zookeeper',
  $ensure_cron       = true,
  $packages          = ['zookeeper'],
  $manual_clean      = false
) {

  # allow installing multiple packages, like zookeeper, zookeeper-bin etc.
  ensure_resource('package', $packages, {'ensure' => $ensure})


  # if !$cleanup_count, then ensure this cron is absent.
  if ($manual_clean and $snap_retain_count > 0 and $ensure != 'absent') {

    if ($ensure_cron){
      ensure_resource('package', 'cron', {
        ensure => 'installed',
      })

      cron { 'zookeeper-cleanup':
          ensure  => present,
          command => "${cleanup_sh} ${datastore} ${snap_retain_count}",
          hour    => 2,
          minute  => 42,
          user    => $user,
          require => Package['zookeeper'],
      }
    }else {
      file { '/etc/cron.daily/zkcleanup':
        ensure  => present,
        content =>  "${cleanup_sh} ${datastore} ${snap_retain_count}",
        require => Package['zookeeper'],
      }
    }
  }

  # package removal
  if($manual_clean and $ensure == 'absent'){
    if ($ensure_cron){
      cron { 'zookeeper-cleanup':
        ensure  => $ensure,
      }
    }else{
      file { '/etc/cron.daily/zkcleanup':
        ensure  => $ensure,
      }
    }
  }
}