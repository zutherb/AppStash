class basenode() {
  Exec { path => ["/usr/bin", "/usr/sbin", "/bin", "/sbin"] }

  package { ['python-setuptools', 'python-protobuf']:
    ensure => present
  }

  include docker
}
