# TikFake 更新日志

## 2-18
1. 加入了JWT来进行token的create与verify 
2. 使用spirng自带的DigestUtils的MD5加密来进行用户密码的加密 
3. 将一些login regisiter操作放到service层中

问题
1. 加入的JWT的toke有过期时间，如果过期了要重新登录，
这个操作感觉需要前后端联合进行开发，单独的后端开发暂时还没想好如何开发

