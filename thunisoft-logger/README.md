## `Thunisoft-logger`（日志工具库）

### `Changelog`：
- `1.0.0`
    - 增加logback
    - 适配androidX
    - 统一封装logger初始化动作
    - 修正保存文件格式错误问题，修改存储目录

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
    // logger
    implementation 'com.thunisoft.android:logger:版本号
    ```
    
- `logger`库需要如下初始化logger

  ***废弃***
  ```java
  /**
     * 初始化logger
     */
    private void initLogger() {
        //log打印格式策略
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Demo")
                .build();
  
        //android log 开关
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
  
        //log存储文件格式策略
        Logger.addLogAdapter(new DiskLogAdapter(DiskFormatStrategy.newBuilder().build(this)));
    }
  ```
  ***变更为***
  ```java
      ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return "xxxx";
            }
  
            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
  ```