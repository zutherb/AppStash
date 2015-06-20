# == Class: marathon
#
# Installs marathon
#
# === Parameters
#
# [*version*]
#   Which version of marathon to install.
#
# [*source_install*]
#   true or false to decide whether marathon is installed by download or package.
#   We currently only support download based install. Default is true
#
# [*download_url*]
#   Where to download marathon from. Note that this url if specified, must either
#   end with a archive extension (.gz, .gzip, .zip) or else '.zip' will be assumed.
#   If this does not match up, then the module will fail to install marathon,
#
# [*download_extract_dir*]
#   Where to extract marathon to. This is just a temporary storage until the app
#   is copied into $install_dir.
#
# [*install_dir*]
#   Where to copy marathon to.
#
# [*user*]
#   The user the marathon app is chowned to.
#
# [*group*]
#   The group the marathon app is chowned to.
#
# === Examples
#
#  class { marathon:
#    version => '0.6.0',
#  }
#
# === Authors
#
# Author Name <william.leese@meltwater.com>
#
class marathon (
  $version = undef,
  $ensure = running,
  $source_install = true,
  $install_java = true,
  $download_url = undef,
  $download_extract_dir = '/tmp',
  $install_dir = '/opt/marathon',
  $user = 'root',
  $group = 'root',
  $package = 'marathon',
  $package_ensure = 'installed',
  $marathon_assets_path = undef,
  $marathon_checkpoint = undef,
  $marathon_event_subscriber = undef,
  $marathon_executor = undef,
  $marathon_failover_timeout = undef,
  $marathon_ha = undef,
  $marathon_hostname = undef,
  $marathon_http_credentials = undef,
  $marathon_http_endpoints = undef,
  $marathon_http_port = undef,
  $marathon_https_port = undef,
  $marathon_local_port_max = undef,
  $marathon_local_port_min = undef,
  $marathon_master = 'local',
  $marathon_mesos_role = undef,
  $marathon_mesos_user = undef,
  $marathon_reconciliation_frequency = undef,
  $marathon_reconciliation_initial_delay = undef,
  $marathon_ssl_keystore_password = undef,
  $marathon_ssl_keystore_path = undef,
  $marathon_task_launch_timeout = undef,
  $marathon_task_rate_limit = undef,
  $marathon_zk = 'zk://localhost:2181/marathon',
  $marathon_zk_hosts = undef,
  $marathon_zk_state = undef,
  $marathon_zk_timeout = undef,
) {

  if $source_install {
    class { 'marathon::source':
      version              => $version,
      download_url         => $download_url,
      download_extract_dir => $download_extract_dir,
      install_dir          => $install_dir,
      user                 => $user,
      group                => $group,
    }
  } else {
    class { 'marathon::package':
      ensure  => $package_ensure,
      package => $package,
    }->
    class { 'marathon::service':
      ensure => $ensure,
    }
  }

  if $install_java {
    package { 'java-1.7.0-openjdk':
      ensure => installed,
    }
  }
}
