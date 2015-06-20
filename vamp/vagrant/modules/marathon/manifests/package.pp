# class marathon::package
class marathon::package (
  $package = undef,
  $ensure = 'installed',
) {

  if $package {
    $package_real = $package
  } else {
    $package_real = 'marathon'
  }

  package { $package:
    ensure => $ensure,
    notify => Class['Marathon::Service'],
  }
}
