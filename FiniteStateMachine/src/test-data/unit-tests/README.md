# 測試案例說明

## 目錄結構
```
src/test-data/unit-tests/     # 測試案例檔案
├── *.in                      # 輸入檔案（如果有的話）
├── *.out                     # 期望輸出檔案
└── README.md                 # 本說明檔案

src/test/java/unittest/       # 測試程式碼
├── UnitTest01.java           # 對應測試類別
├── UnitTest02.java
└── ...
```

## 測試案例與程式碼對應關係

每個測試程式碼檔案（UnitTestXX.java）都會：
1. 在第19行定義 `expectedFilePath` 變數
2. 指向對應的 `.out` 檔案作為期望輸出
3. 執行測試後比較實際輸出與期望輸出

## 範例
```java
// UnitTest01.java 第19行
String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/ChatBot_Default_InitialMessageResponse.out";
```

對應的測試案例檔案：
- `ChatBot_Default_InitialMessageResponse.in` - 輸入事件序列
- `ChatBot_Default_InitialMessageResponse.out` - 期望輸出結果

## 測試案例命名規則
- 格式：`功能_狀態_情境.in/.out`
- 範例：`ChatBot_Default_InitialMessageResponse.in/.out`
- 說明：聊天機器人在預設狀態下的初始訊息回應

## 輸入檔案格式 (.in)
每個 `.in` 檔案包含一系列事件，格式為：`[事件名稱] JSON格式的事件內容`

詳細的事件格式說明請參考：[README-IN-SPEC.md](./README-IN-SPEC.md)

### 常見事件範例：
```
[started] {"time": "2023-08-07 00:00:00", "quota": 20}
[login] {"userId": "1", "isAdmin": false}
[new message] {"authorId": "1", "content": "大家好～", "tags": []}
[end]
```
