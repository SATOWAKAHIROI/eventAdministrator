# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

イベント管理システム - 管理者用画面、クライアント用画面、バックエンドAPIの3層構成

- **adminPage**: 管理者用フロントエンド (React + Vite + TypeScript, ポート3000)
- **clientPage**: クライアント用フロントエンド (React + Vite + TypeScript, ポート3001)
- **backend**: Spring Boot RESTful API (Java 17 + Maven, ポート8080)
- **データベース**: MySQL 8.0 (ポート3306)

## 開発コマンド

### Dockerを使用した開発（推奨）

```bash
# 全サービスを起動
docker-compose up -d

# 特定のサービスを再起動
docker-compose restart backend
docker-compose restart admin-page
docker-compose restart client-page

# ログを確認
docker-compose logs -f backend
docker-compose logs -f admin-page

# 全サービスを停止
docker-compose down
```

### ローカル開発

**バックエンド** (`backend/`)
```bash
# ビルド
./mvnw clean install

# 実行
./mvnw spring-boot:run

# テスト実行
./mvnw test

# 特定のテストクラスを実行
./mvnw test -Dtest=BackendApplicationTests
```

**フロントエンド** (`adminPage/` または `clientPage/`)
```bash
# 依存関係のインストール
npm install

# 開発サーバー起動
npm run dev

# ビルド
npm run build

# Lint
npm run lint

# プレビュー（ビルド後）
npm run preview
```

## アーキテクチャ

### バックエンド構成（Spring Boot）

標準的なレイヤードアーキテクチャ:

```
com.example.backend/
├── controller/        # REST エンドポイント
│   ├── admin/        # 管理者用API (ROLE_ADMIN必要)
│   └── client/       # クライアント用API (認証必要)
├── service/          # ビジネスロジック
│   ├── admin/
│   └── client/
├── repository/       # データアクセス層 (Spring Data JPA)
├── entity/           # JPA エンティティ
├── dto/              # データ転送オブジェクト
│   ├── requests/     # リクエストDTO
│   ├── response/     # レスポンスDTO
│   └── common/       # 共通DTO (ApiResponse等)
├── security/         # JWT認証関連
│   ├── JwtTokenProvider.java
│   └── JwtAuthenticationFilter.java
├── config/           # 設定クラス (SecurityConfig等)
└── exception/        # 例外ハンドリング
```

**主要な設計パターン:**
- Controller層とService層でadmin/clientを明確に分離
- JWT認証: `/admin/**` はROLE_ADMIN、`/client/**` は認証済みユーザー
- 認証不要エンドポイント: `/admin/user/login`, `/admin/user/signup`, `/client/user/login`, `/client/user/signup`
- グローバル例外ハンドリング (GlobalExceptionHandler)
- カスタム例外: BadRequestException, ResourceNotFoundException, UnauthorizedException, BusinessException

### フロントエンド構成（React + TypeScript）

adminPageとclientPageは同じ構造:

```
src/
├── pages/            # ページコンポーネント
│   └── auth/        # 認証関連ページ
├── service/         # API通信ロジック
├── hooks/           # カスタムフック
├── context/         # React Context
├── utils/           # ユーティリティ (api.ts等)
└── common/          # 共通コンポーネント/型定義
```

**API通信:**
- `utils/api.ts`: Axiosインスタンス設定
- リクエストインターセプター: localStorageからtokenを取得し、Authorization headerに設定
- レスポンスインターセプター: 401エラー時に自動的にログアウト処理
- API Base URL: 環境変数 `VITE_API_URL` (デフォルト: `http://localhost:8080/api`)

**認証フロー:**
1. ログイン成功時にJWTトークンとユーザー情報をlocalStorageに保存
2. 全てのAPI通信でBearerトークンを自動付与
3. 401エラー時にlocalStorageをクリアして `/login` にリダイレクト

## 環境変数

**バックエンド** (docker-compose.ymlで設定)
- `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`, `DB_NAME`
- `SERVER_PORT`
- `SPRING_JPA_HIBERNATE_DDL_AUTO`

**フロントエンド**
- `VITE_API_URL`: バックエンドAPIのベースURL

## データベース

- MySQL 8.0
- 文字セット: utf8mb4
- タイムゾーン: Asia/Tokyo
- Spring JPA の `ddl-auto: update` でスキーマ自動更新（開発環境）
