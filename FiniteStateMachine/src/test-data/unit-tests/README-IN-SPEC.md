輸入格式十分統一：每一行代表發生「一個事件」。

#每一行的格式為：[<event's name>] <payload in JSON format> ，有兩項組成：
- event’s name ：事件的英文名稱，不同事件會有不同的名稱。
- payload in JSON format ：此事件的「內容」，由於內容以多項欄位組成，為了方便起見，以 JSON 格式表達。有些事件沒有內容，那該行輸入就不會存在 payload。
以下為事件列表，記載每一個事件的名稱以及該事件內容 payload 的欄位描述：

## 開始
事件名稱：[started]
payload:
- time ：時間格式的字串，格式為 YYYY-MM-DD HH:mm:ss ，代表執行此次機器人程式的模擬初始時間。
- quota：大於 0 的整數，代表機器人的初始額度。
範例：[started] {"time": "2023-08-07 00:00:00", "quota": 10}

## 登入
事件名稱：[login]
payload:
- userId ：字串，為登入成員的 ID
- isAdmin ：登入成員是否為管理員
範例：[login] {"userId": "1", "isAdmin": true}

## 登出
事件名稱：[logout]
payload:
- userId ：字串，為登出成員的 ID
範例：[logout] {"userId": "1"}

## 時間流逝：
事件名稱：[<n> <time-unit> elapsed]
- n 為正整數（包含 0）。
- time-unit 為時間單位，只有以下幾種可能的值：秒 (seconds)、分鐘(minutes)、小時(hours)。
解釋：這件事件的用途是「模擬在現實世界中的時間流逝」，在一些情境下，機器人會根據「時間流逝」來決定接續的行為。好比在「知識王」的感謝參與狀態（ThanksForJoining）中需求提及，在 20 秒過後，機器人就會結束知識王狀態，並且回到正常狀態。
沒有 payload。
範例：[10 seconds elapsed]

## 聊天室中有新訊息：
事件名稱：[new message]
payload:
- authorId ：字串，為訊息發送者的 ID
- content ：字串（最多有 1000 個字元），代表此訊息的內容。
- tags ：為一個字串陣列，存放所有此訊息標記的用戶 Id。
範例：[new message] {"authorId": "5", "content": "嗯，今天大家都在幹嘛？", "tags": ["1", "3", "bot"]}

## 論壇中有人發布新貼文：
事件名稱：[new post]
payload:
- id ：字串，為此貼文的 ID，保證不與其他貼文重複。
- authorId ：字串，為貼文作者的 ID
- title ：字串，為貼文標題（最多有 50 個字元）
- content ：字串（最多有 1000 個字元），為此貼文的內容。
- tags ：為一個字串陣列，存放所有此貼文內容標記的用戶 Id。
範例：[new post] {"id": "1", "authorId": "8", "title": "分享一個關於 單一職責原則 的笑話，每次講起來都還是覺得很好笑", "content": "(1) 欸你這個類別這樣做太多事了吧，違反單一職責原則啊，每個類別只能有一個職責，只能做一件事。 (2) 這個類別，確實只做一件事，那就是實現需求！", "tags": ["1", "2", "3"]}

## 開始廣播
事件名稱：[go broadcasting] ，代表有成員開始廣播。
payload:
- speakerId：字串，為廣播者的 ID
範例：[go broadcasting] {"speakerId": "4"}

## 廣播中傳遞語音訊息
事件名稱：[speak] ，代表廣播成員正在傳遞語音訊息。
payload:
- speakerId：字串，為廣播者的 ID
- content：字串，為廣播者正在傳遞的語音訊息內容
範例：[speak] {"speakerId": "4", "content": "大家早安"}

## 停止廣播
事件名稱：[stop broadcasting] ，代表有成員停止廣播。
payload:
- speakerId：字串，為廣播者的 ID
範例：[stop broadcasting] {"speakerId": "4"}

## 程式終止
事件名稱：[end]，代表整份輸入和程式的終止。