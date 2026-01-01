import { useEffect, useState } from "react";
import clientEventService from "../service/clientEventService";

type Event = {
  id: number;
  name: string;
  overview: string;
  startDate: string;
  endDate: string;
  createdById: number;
};

type ModelData = {
  name: string;
  overview: string;
  startDate: string;
  endDate: string;
};

export default function Events() {
  const [eventList, setEventList] = useState<Event[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [showModel, setShowModel] = useState<boolean>(false);
  const [modelData, setModelData] = useState<ModelData>({
    name: "",
    overview: "",
    startDate: "",
    endDate: "",
  });

  useEffect(() => {
    fetchEventList();
  }, []);

  const fetchEventList = async () => {
    setLoading(true);
    try {
      const response = await clientEventService.getAllEvent();
      setEventList(response.data);
    } catch (err: any) {
      if (err.response?.data?.error?.message) {
        setError(err.response.data.error.message);
      } else {
        setError("認証に失敗しました。(サーバーエラー)");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleShowModel = (event: Event) => {
    setModelData({
      name: event.name,
      overview: event.overview,
      startDate: event.startDate,
      endDate: event.endDate,
    });
    setShowModel(true);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">イベント一覧</h1>
        </div>

        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 text-red-800 px-4 py-3 rounded-lg">
            <p className="text-sm">{error}</p>
          </div>
        )}

        {loading ? (
          <div className="flex justify-center items-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
          </div>
        ) : eventList.length > 0 ? (
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
                  <div className="ml-4">
                    <button
                      disabled={loading}
                      onClick={() => handleShowModel(event)}
                      className="px-4 py-2 text-sm bg-indigo-50 text-indigo-700 rounded-lg hover:bg-indigo-100 focus:outline-none focus:ring-2 focus:ring-indigo-500 disabled:opacity-50 transition-colors"
                    >
                      詳細表示
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
                  <h2 className="text-2xl font-bold text-gray-900">イベント詳細</h2>
                  <button
                    onClick={() => setShowModel(false)}
                    className="text-gray-400 hover:text-gray-600 transition-colors"
                  >
                    <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                    </svg>
                  </button>
                </div>

                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      イベント名
                    </label>
                    <p className="text-gray-900 bg-gray-50 px-3 py-2 rounded-lg">{modelData.name}</p>
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      概要
                    </label>
                    <p className="text-gray-900 bg-gray-50 px-3 py-2 rounded-lg whitespace-pre-wrap">{modelData.overview}</p>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">
                        開始日
                      </label>
                      <p className="text-gray-900 bg-gray-50 px-3 py-2 rounded-lg">{modelData.startDate}</p>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">
                        終了日
                      </label>
                      <p className="text-gray-900 bg-gray-50 px-3 py-2 rounded-lg">{modelData.endDate}</p>
                    </div>
                  </div>

                  <div className="pt-4">
                    <button
                      type="button"
                      onClick={() => setShowModel(false)}
                      className="w-full px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition-colors"
                    >
                      閉じる
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
