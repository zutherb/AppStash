# Class: zookeeper::install
#
# This module manages Zookeeper installation
#
# Parameters: None
#
# Actions: None
#
#
# Should not be included directly
#
class zookeeper::install(
  $ensure            = present,
  $snap_retain_count = 3,
  $cleanup_sh        = '/usr/lib/zookeeper/bin/zkCleanup.sh',
  $datastore         = '/var/lib/zookeeper',
  $user              = 'zookeeper',
  $start_with        = 'init.d',
  $ensure_cron       = true,
  $service_package   = 'zookeeperd',
  $packages          = ['zookeeper']
) {
  anchor { 'zookeeper::install::begin': }
  anchor { 'zookeeper::install::end': }

  case $::osfamily {
    Debian: {
      class { 'zookeeper::os::debian':
        ensure            => $ensure,
        snap_retain_count => $snap_retain_count,
        cleanup_sh        => $cleanup_sh,
        datastore         => $datastore,
        user              => $user,
        start_with        => $start_with,
        ensure_cron       => $ensure_cron,
        service_package   => $service_package,
        packages          => $packages,
        before            => Anchor['zookeeper::install::end'],
        require           => Anchor['zookeeper::install::begin'],
      }
    }
    RedHat: {
      class { 'zookeeper::os::redhat':
        ensure            => $ensure,
        snap_retain_count => $snap_retain_count,
        cleanup_sh        => $cleanup_sh,
        datastore         => $datastore,
        user              => $user,
        ensure_cron       => $ensure_cron,
        packages          => $packages,
        require           => Anchor['zookeeper::install::begin'],
        before            => Anchor['zookeeper::install::end'],
      }
    }
    default: {
      fail("Module '${module_name}' is not supported on OS: '${::operatingsystem}', family: '${::osfamily}'")
    }
  }
}

