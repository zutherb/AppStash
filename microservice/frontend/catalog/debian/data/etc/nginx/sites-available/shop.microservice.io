http {
    proxy_cache_path /data/nginx/cache keys_zone=one:10m;

    proxy_cache one;

    server {
            listen   80; ## listen for ipv4; this line is default and implied
            #listen   [::]:80 default ipv6only=on; ## listen for ipv6

            root /usr/share/shop/frontend/catalog/html;
            index index.html index.htm;

            # Make site accessible from http://localhost/
            server_name shop.microservice.io;

            location /api/product/ {
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

                proxy_pass http://localhost:18080;
            }
    }
}
