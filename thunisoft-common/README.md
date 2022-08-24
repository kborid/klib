## `Thunisoft-common`（基础工具库）

### `Changelog`：
- `1.0.0`
  - 适配androidX
  - 优化网络请求异常处理
  - 增加网络请求通用框架
  - 增加bitmap工具类
  - 增加Fragment懒加载base类
  - 增加公共基础工具类库，基类

## 如何使用？

- 在项目`build.gradle`文件中加入如下代码：

  ```groovy
  allprojects {
      repositories {
          maven { 
              url "http://repo.thunisoft.com/maven2/content/repositories/releases/" 
          }
      }
  }
  ```



- 在需要引用的module的`build.gradle`文件中加入如下代码：

    ```groovy
    // common
    implementation 'com.thunisoft.android:common:版本号
    ```
    
- 需要在Application中初始化

  ```
  ThunisoftCommon.init()
  ```
  
  
