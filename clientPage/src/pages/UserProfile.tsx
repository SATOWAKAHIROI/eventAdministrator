import { useAuth } from "../hooks/useAuth";

export default function UserProfile() {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="bg-white rounded-xl shadow-lg p-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-6">ユーザー情報</h1>

          <div className="space-y-6">
            <div className="border-b border-gray-200 pb-4">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                ユーザーID
              </label>
              <p className="text-lg text-gray-900">{user?.id}</p>
            </div>

            <div className="border-b border-gray-200 pb-4">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                名前
              </label>
              <p className="text-lg text-gray-900">{user?.name}</p>
            </div>

            <div className="border-b border-gray-200 pb-4">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                メールアドレス
              </label>
              <p className="text-lg text-gray-900">{user?.email}</p>
            </div>

            <div className="border-b border-gray-200 pb-4">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                ユーザーロール
              </label>
              <p className="text-lg text-gray-900">
                <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
                  {user?.userRole}
                </span>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
