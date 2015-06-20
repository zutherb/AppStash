# zookeeper host

define zookeeper::host($hostname = $title, $id, $client_ip, $election_port, $leader_port) {
  datacat_fragment { $hostname:
        target => '/etc/zookeeper/conf/quorum.conf',
        data   => {
            'id'            => $id,
            'client_ip'     => $client_ip,
            'election_port' => $election_port,
            'leader_port'   => $leader_port,
        },
  }
}