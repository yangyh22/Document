## spring cloud 下线，删除微服务的curl

 删除 

```bash
curl -X DELETE \
  http://192.168.0.143:50000/eureka/apps/GOODSCENTER/192.168.0.143:40001 \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: ' \
  -H 'Host: 192.168.0.143:50000' \
  -H 'Postman-Token: fcb3b76b-bebb-4e22-aa8e-b7bae80fb798,9501b991-62cb-45b8-b3f4-184210f05e99' \
  -H 'User-Agent: PostmanRuntime/7.15.2' \
  -H 'cache-control: no-cache'

```





 下线 

```bash
curl -X PUT \
  'http://192.168.0.143:50000/eureka/apps/GOODSCENTER/192.168.0.143:40001/status?value=OUT_OF_SERVICE' \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: ' \
  -H 'Host: 192.168.0.143:50000' \
  -H 'Postman-Token: 787ec4a5-e4d4-4c2a-a12c-ffbccd046462,7df0f2e3-5b58-41a2-9c21-257fe9f57061' \
  -H 'User-Agent: PostmanRuntime/7.15.2' \
  -H 'cache-control: no-cache'
```

