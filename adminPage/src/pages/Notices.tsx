import { useEffect, useState, type FormEvent } from "react";
import { useAuth } from "../hooks/useAuth";
import adminNoticeService from "../service/adminNoticeService";

type Notice = {
  id: number;
  subject: string;
  body: string;
  createdById: number;
  updated_at: string;
};

type FormData = {
  subject: string;
  body: string;
};

export default function Notices() {
  const [noticeList, setNoticeList] = useState<Notice[]>([]);
  const [formData, setFormData] = useState<FormData>({
    subject: "",
    body: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [showModel, setShowModel] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [formError, setFormError] = useState<string>("");
  const [editingNotice, setEditingNotice] = useState<Notice | null>(null);

  const { user } = useAuth();

  useEffect(() => {
    fetchNoticeList();
  }, []);

  const fetchNoticeList = async () => {
    setLoading(true);
    try {
      const response = await adminNoticeService.getAllNotice();
      setNoticeList(response.data);
    } catch (err: any) {
      // エラーレスポンスからメッセージを取得
      if (err.response?.data?.error?.message) {
        setError(err.response.data.error.message);
      } else {
        setError("認証に失敗しました。(サーバーエラー)");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setEditingNotice(null);
    setFormData({
      subject: "",
      body: "",
    });
    setShowModel(true);
  };

  const handleEdit = (notice: Notice) => {
    setEditingNotice(notice);
    setFormData({
      subject: notice.subject,
      body: notice.body,
    });
    setShowModel(true);
  };

  const handleDelete = async (notice: Notice) => {
    if (!window.confirm(`「${notice.subject}」を本当に削除してもよろしいでしょうか？`)) {
      return;
    }

    setLoading(true);

    try {
      await adminNoticeService.deleteNoticeById(notice.id);
    } catch (err: any) {
      // エラーレスポンスからメッセージを取得
      if (err.response?.data?.error?.message) {
        setError(err.response.data.error.message);
      } else {
        setError("認証に失敗しました。(サーバーエラー)");
      }
    } finally {
      setLoading(false);
      fetchNoticeList();
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (!!editingNotice) {
        await adminNoticeService.editEventById(
          formData.subject,
          formData.body,
          editingNotice.id
        );
      } else {
        await adminNoticeService.createNotice(
          formData.subject,
          formData.body,
          user!.id
        );
      }
    } catch (err: any) {
      // エラーレスポンスからメッセージを取得
      if (err.response?.data?.error?.message) {
        setFormError(err.response.data.error.message);
      } else {
        setFormError("認証に失敗しました。(サーバーエラー)");
      }
    } finally {
      setEditingNotice(null);
      setLoading(false);
      setShowModel(false);
      fetchNoticeList();
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900">お知らせ一覧</h1>
          <button
            disabled={loading}
            onClick={() => handleCreate()}
            className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            + お知らせ作成
          </button>
        </div>

        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 text-red-800 px-4 py-3 rounded-lg">
            <p className="text-sm">{error}</p>
          </div>
        )}

        {noticeList.length > 0 ? (
          <div className="grid grid-cols-1 gap-6">
            {noticeList.map((notice) => (
              <div
                key={notice.id}
                className="bg-white rounded-xl shadow-md p-6 border border-gray-200 hover:shadow-lg transition-shadow"
              >
                <div className="flex justify-between items-start">
                  <div className="flex-1">
                    <h2 className="text-xl font-semibold text-gray-900 mb-2">{notice.subject}</h2>
                    <p className="text-gray-600 mb-4 whitespace-pre-wrap">{notice.body}</p>
                    <div className="flex items-center text-sm text-gray-500">
                      <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                      <span>最終更新: {notice.updated_at}</span>
                    </div>
                  </div>
                  <div className="flex gap-2 ml-4">
                    <button
                      disabled={loading}
                      onClick={() => handleEdit(notice)}
                      className="px-3 py-1 text-sm bg-blue-50 text-blue-700 rounded-lg hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 transition-colors"
                    >
                      編集
                    </button>
                    <button
                      disabled={loading}
                      onClick={() => handleDelete(notice)}
                      className="px-3 py-1 text-sm bg-red-50 text-red-700 rounded-lg hover:bg-red-100 focus:outline-none focus:ring-2 focus:ring-red-500 disabled:opacity-50 transition-colors"
                    >
                      削除
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="bg-white rounded-xl shadow-md p-12 text-center">
            <svg className="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5.882V19.24a1.76 1.76 0 01-3.417.592l-2.147-6.15M18 13a3 3 0 100-6M5.436 13.683A4.001 4.001 0 017 6h1.832c4.1 0 7.625-1.234 9.168-3v14c-1.543-1.766-5.067-3-9.168-3H7a3.988 3.988 0 01-1.564-.317z" />
            </svg>
            <p className="text-gray-500">お知らせは登録されていません</p>
          </div>
        )}

        {showModel && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
            <div className="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
              <div className="p-6">
                <div className="flex justify-between items-center mb-6">
                  <h2 className="text-2xl font-bold text-gray-900">
                    {editingNotice ? "お知らせ編集" : "お知らせ作成"}
                  </h2>
                  <button
                    onClick={() => setShowModel(false)}
                    className="text-gray-400 hover:text-gray-600 transition-colors"
                  >
                    <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                    </svg>
                  </button>
                </div>

                {formError && (
                  <div className="mb-6 bg-red-50 border border-red-200 text-red-800 px-4 py-3 rounded-lg">
                    <p className="text-sm">{formError}</p>
                  </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-6">
                  <div>
                    <label htmlFor="subject" className="block text-sm font-medium text-gray-700 mb-1">
                      件名
                    </label>
                    <input
                      id="subject"
                      name="subject"
                      type="text"
                      required
                      placeholder="件名"
                      value={formData.subject}
                      onChange={(e) =>
                        setFormData({ ...formData, subject: e.target.value })
                      }
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500"
                    />
                  </div>

                  <div>
                    <label htmlFor="body" className="block text-sm font-medium text-gray-700 mb-1">
                      本文
                    </label>
                    <textarea
                      name="body"
                      id="body"
                      required
                      placeholder="本文"
                      rows={8}
                      value={formData.body}
                      onChange={(e) =>
                        setFormData({ ...formData, body: e.target.value })
                      }
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500"
                    ></textarea>
                  </div>

                  <div className="flex gap-3 pt-4">
                    <button
                      type="submit"
                      disabled={loading}
                      className="flex-1 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                      {loading ? "送信中..." : "送信"}
                    </button>
                    <button
                      type="button"
                      onClick={() => setShowModel(false)}
                      className="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition-colors"
                    >
                      キャンセル
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
