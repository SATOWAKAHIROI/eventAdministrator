export default function Overview() {
  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-6">管理画面の概要</h1>

          <div className="space-y-6">
            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">このシステムについて</h2>
              <p className="text-gray-600 leading-relaxed">
                この管理画面では、イベントとお知らせの管理を行うことができます。
                管理者権限を持つユーザーのみがアクセス可能です。
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">主な機能</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="bg-indigo-50 border border-indigo-200 rounded-lg p-4">
                  <h3 className="font-semibold text-indigo-900 mb-2">イベント管理</h3>
                  <ul className="text-sm text-indigo-700 space-y-1">
                    <li>• イベントの作成</li>
                    <li>• イベント情報の編集</li>
                    <li>• イベントの削除</li>
                    <li>• イベント一覧の閲覧</li>
                  </ul>
                </div>

                <div className="bg-green-50 border border-green-200 rounded-lg p-4">
                  <h3 className="font-semibold text-green-900 mb-2">お知らせ管理</h3>
                  <ul className="text-sm text-green-700 space-y-1">
                    <li>• お知らせの作成</li>
                    <li>• お知らせの編集</li>
                    <li>• お知らせの削除</li>
                    <li>• お知らせ一覧の閲覧</li>
                  </ul>
                </div>
              </div>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">使い方</h2>
              <ol className="list-decimal list-inside space-y-2 text-gray-600">
                <li>ホームページから管理したい項目を選択</li>
                <li>一覧ページで現在の登録情報を確認</li>
                <li>作成ボタンから新規登録、または編集・削除ボタンで既存データを管理</li>
              </ol>
            </section>

            <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4 mt-6">
              <div className="flex items-start">
                <svg className="w-5 h-5 text-yellow-600 mt-0.5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                </svg>
                <div>
                  <h3 className="font-semibold text-yellow-900 mb-1">注意事項</h3>
                  <p className="text-sm text-yellow-700">
                    削除操作は取り消すことができません。重要なデータを削除する前に、必ず内容を確認してください。
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
