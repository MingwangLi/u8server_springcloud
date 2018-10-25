define({ "api": [
  {
    "type": "POST",
    "url": "/admin/doLogin",
    "title": "登陆",
    "group": "UserGroup",
    "name": "__",
    "description": "<p>用户登陆接口</p>",
    "version": "1.0.0",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "args-Example",
          "content": "{\n\"username\":\"jmsht_game\",\n\"password\":\"jmsht1405\"\n}",
          "type": "Object"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\"code\": 200,\n\"msg\": \"登陆成功\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response",
          "content": "HTTP/1.1\n{\n\"code\": 201,\n\"msg\":\"密码错误\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/admin/doLogin"
      }
    ],
    "filename": "./src/main/java/com/jmsht/u8server/admin/web/controller/LoginController.java",
    "groupTitle": "用户模块"
  }
] });
