export default function Overview() {
  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-6">クライアントページの概要</h1>

          <div className="space-y-6">
            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">このシステムについて</h2>
              <p className="text-gray-600 leading-relaxed">
                このクライアントページでは、イベントとお知らせの情報を閲覧することができます。
                最新のイベント情報や重要なお知らせを確認できます。
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">主な機能</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="bg-indigo-50 border border-indigo-200 rounded-lg p-4">
                  <h3 className="font-semibold text-indigo-900 mb-2">イベント情報</h3>
                  <ul className="text-sm text-indigo-700 space-y-1">
                    <li>• 開催予定のイベント一覧の閲覧</li>
                    <li>• イベント詳細情報の確認</li>
                    <li>• 開催日時の確認</li>
                    <li>• イベント概要の閲覧</li>
                  </ul>
                </div>

                <div className="bg-green-50 border border-green-200 rounded-lg p-4">
                  <h3 className="font-semibold text-green-900 mb-2">お知らせ情報</h3>
                  <ul className="text-sm text-green-700 space-y-1">
                    <li>• 最新のお知らせ一覧の閲覧</li>
                    <li>• お知らせの詳細確認</li>
                    <li>• 更新日時の確認</li>
                    <li>• 重要な通知の受信</li>
                  </ul>
                </div>
              </div>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-gray-800 mb-3">使い方</h2>
              <ol className="list-decimal list-inside space-y-2 text-gray-600">
                <li>ホームページから確認したい情報を選択</li>
                <li>イベント一覧またはお知らせ一覧ページで情報を確認</li>
                <li>詳細表示ボタンをクリックして詳しい情報を閲覧</li>
              </ol>
            </section>

            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mt-6">
              <div className="flex items-start">
                <svg className="w-5 h-5 text-blue-600 mt-0.5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
                </svg>
                <div>
                  <h3 className="font-semibold text-blue-900 mb-1">ご案内</h3>
                  <p className="text-sm text-blue-700">
                    イベントやお知らせは定期的に更新されます。最新の情報を確認するために、定期的にページをご覧ください。
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
