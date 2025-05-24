# 🚌 スクールバス予約システム（運転手用アプリ・Java版）

**スクールバス運行のための専用Androidアプリ（Java版）**です。  
運転手が予約状況を確認し、運行情報を管理できるように設計されています。

- **[🟦 デモ動画を見る](https://youtube.com/shorts/awHSWGFRi0o?feature=share)**  
- 2024年 大邱カトリック大学 キャップストーンデザイン 🥉奨励賞 受賞作品  
- **📄 [🇰🇷 한국어版READMEはこちら](./README.md)**  

---

## 🔧 主な機能

### 1. ログイン／会員登録
- [ログイン画面](driverImages/driverlogin.PNG)  
- [会員登録画面](driverImages/userregister.PNG)

- Firebase Authentication を利用したメール／パスワードログイン  
- Firebase 認証連携により、アカウント登録・ログイン処理を実装  

---

### 2. 路線・時間の選択
- [画面レイアウト](driverImages/driverroutetime.PNG)

- Spinner（ドロップダウン）を使用して、運転する路線と時間を選択  
- 選択した情報は次の画面（予約状況）に引き継がれます  

---

### 3. 予約状況の確認
- [画面レイアウト](driverImages/driverclock.PNG)

- Firebase Firestore から予約データを取得  
- 停留所ごとの予約者数を表示し、リアルタイムで人数が更新されます  
- 視認性を考慮し、乗車人数によって UI が変化します  

---

### 4. その他の機能
- ログアウト処理  
- 予約データの削除機能（テスト用またはミス対応用）

---

## ⚙️ 使用技術

| 項目 | 内容 |
|------|------|
| 言語 | Java |
| UI | Android XML |
| バックエンド | Firebase Firestore |
| 認証 | Firebase Authentication |
| 使用コンポーネント | RecyclerView、Spinner、Intentなど |

---

> 🚍 本プロジェクトは、実際のスクールバス運行業務を想定して運転手目線で機能を設計・実装したアプリです。  
> Android Studio と Firebase を活用し、**実用性・直感性・安全性** に重点を置いて開発しました。
