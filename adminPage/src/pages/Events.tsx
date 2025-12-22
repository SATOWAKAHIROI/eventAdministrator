import { useEffect, useState, type FormEvent } from "react";
import adminEventService from "../service/adminEventService";
import { useAuth } from "../hooks/useAuth";

type Event = {
  id: number;
  name: string;
  overview: string;
  startDate: string;
  endDate: string;
  createdById: number;
};

type FormData = {
  name: string;
  overview: string;
  startDate: string;
  endDate: string;
};

export default function Events() {
  const [eventList, setEventList] = useState<Event[]>([]);
  const [formData, setFormData] = useState<FormData>({
    name: "",
    overview: "",
    startDate: "",
    endDate: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [showModel, setShowModel] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [formError, setFormError] = useState<string>("");
  const [editingEvent, setEditingEvent] = useState<Event | null>(null);

  const { user } = useAuth();

  useEffect(() => {
    fetchEventList();
  }, []);

  const fetchEventList = async () => {
    setLoading(true);
    try {
      const response = await adminEventService.getAllEvent();
      setEventList(response.data);
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
    setEditingEvent(null);
    setFormData({
      name: "",
      overview: "",
      startDate: "",
      endDate: "",
    });
    setShowModel(true);
  };

  const handleEdit = (event: Event) => {
    setEditingEvent(event);
    setFormData({
      name: event.name,
      overview: event.overview,
      startDate: event.startDate,
      endDate: event.endDate,
    });
    setShowModel(true);
  };

  const handleDelete = async (event: Event) => {
    setLoading(true);

    try {
      await adminEventService.deleteEventById(event.id);
    } catch (err: any) {
      // エラーレスポンスからメッセージを取得
      if (err.response?.data?.error?.message) {
        setError(err.response.data.error.message);
      } else {
        setError("認証に失敗しました。(サーバーエラー)");
      }
    } finally {
      setLoading(false);
      fetchEventList();
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (!!editingEvent) {
        await adminEventService.editEventById(
          formData.name,
          formData.overview,
          formData.startDate,
          formData.endDate,
          editingEvent.id
        );
      } else {
        await adminEventService.createEvent(
          formData.name,
          formData.overview,
          formData.startDate,
          formData.endDate,
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
      setEditingEvent(null);
      setFormData({
        name: "",
        overview: "",
        startDate: "",
        endDate: "",
      });
      setLoading(false);
      setShowModel(false);
      fetchEventList();
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900">イベント一覧</h1>
          <button
            disabled={loading}
            onClick={() => handleCreate()}
            className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            + イベント作成
          </button>
        </div>

        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 text-red-800 px-4 py-3 rounded-lg">
            <p className="text-sm">{error}</p>
          </div>
        )}

        {eventList.length > 0 ? (
          <div className="grid grid-cols-1 gap-6">
            {eventList.map((event) => (
              <div
                key={event.id}
                className="bg-white rounded-xl shadow-md p-6 border border-gray-200 hover:shadow-lg transition-shadow"
              >
                <div className="flex justify-between items-start">
                  <div className="flex-1">
                    <h2 className="text-xl font-semibold text-gray-900 mb-2">{event.name}</h2>
                    <p className="text-gray-600 mb-4">{event.overview}</p>
                    <div className="flex flex-wrap gap-4 text-sm text-gray-500">
                      <div className="flex items-center">
                        <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                        </svg>
                        <span>開始: {event.startDate}</span>
                      </div>
                      <div className="flex items-center">
                        <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                        </svg>
                        <span>終了: {event.endDate}</span>
                      </div>
                    </div>
                  </div>
                  <div className="flex gap-2 ml-4">
                    <button
                      disabled={loading}
                      onClick={() => handleEdit(event)}
                      className="px-3 py-1 text-sm bg-blue-50 text-blue-700 rounded-lg hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 transition-colors"
                    >
                      編集
                    </button>
                    <button
                      disabled={loading}
                      onClick={() => handleDelete(event)}
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
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
            <p className="text-gray-500">イベントは登録されていません</p>
          </div>
        )}

        {showModel && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
            <div className="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
              <div className="p-6">
                <div className="flex justify-between items-center mb-6">
                  <h2 className="text-2xl font-bold text-gray-900">
                    {editingEvent ? "イベント編集" : "イベント作成"}
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
                    <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">
                      イベント名
                    </label>
                    <input
                      id="name"
                      name="name"
                      type="text"
                      placeholder="イベント名"
                      required
                      value={formData.name}
                      onChange={(e) =>
                        setFormData({ ...formData, name: e.target.value })
                      }
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    />
                  </div>

                  <div>
                    <label htmlFor="overview" className="block text-sm font-medium text-gray-700 mb-1">
                      概要
                    </label>
                    <textarea
                      name="overview"
                      id="overview"
                      placeholder="概要"
                      rows={6}
                      value={formData.overview}
                      onChange={(e) =>
                        setFormData({ ...formData, overview: e.target.value })
                      }
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    ></textarea>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label htmlFor="startDate" className="block text-sm font-medium text-gray-700 mb-1">
                        開始日
                      </label>
                      <input
                        id="startDate"
                        name="startDate"
                        type="date"
                        value={formData.startDate}
                        onChange={(e) =>
                          setFormData({ ...formData, startDate: e.target.value })
                        }
                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      />
                    </div>

                    <div>
                      <label htmlFor="endDate" className="block text-sm font-medium text-gray-700 mb-1">
                        終了日
                      </label>
                      <input
                        id="endDate"
                        name="endDate"
                        type="date"
                        value={formData.endDate}
                        onChange={(e) =>
                          setFormData({ ...formData, endDate: e.target.value })
                        }
                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      />
                    </div>
                  </div>

                  <div className="flex gap-3 pt-4">
                    <button
                      type="submit"
                      disabled={loading}
                      className="flex-1 px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
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
