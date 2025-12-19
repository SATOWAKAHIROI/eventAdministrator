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
    setLoading(loading);
    try {
      const response = await adminEventService.getAllEvent();
      setEventList(response.data);
      console.log(response.data);
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
    <div>
      <h1>イベント一覧</h1>
      {error && (
        <div>
          <p>{error}</p>
        </div>
      )}
      <button onClick={() => handleCreate()}>イベント作成</button>
      {eventList.length > 0 ? (
        <ul>
          {eventList.map((event) => (
            <li key={event.id}>
              <span>{event.name}</span>
              <span>{event.overview}</span>
              <span>{event.startDate}</span>
              <span>{event.endDate}</span>
              <button onClick={() => handleEdit(event)}>編集</button>
              <button onClick={() => handleDelete(event)}>削除</button>
            </li>
          ))}
        </ul>
      ) : (
        <div>
          <p>イベントは登録されていません</p>
        </div>
      )}
      {showModel && (
        <div>
          <form onSubmit={handleSubmit}>
            {formError && (
              <div>
                <p>{formError}</p>
              </div>
            )}
            <label>名前</label>
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
            />
            <label>概要</label>
            <textarea
              name="overview"
              id="overview"
              placeholder="概要"
              rows={10}
              cols={20}
              value={formData.overview}
              onChange={(e) =>
                setFormData({ ...formData, overview: e.target.value })
              }
            ></textarea>
            <label>開始日</label>
            <input
              id="startDate"
              name="startDate"
              type="date"
              value={formData.startDate}
              onChange={(e) =>
                setFormData({ ...formData, startDate: e.target.value })
              }
            />
            <label>終了日</label>
            <input
              id="endDate"
              name="endDate"
              type="date"
              value={formData.endDate}
              onChange={(e) =>
                setFormData({ ...formData, endDate: e.target.value })
              }
            />
            <button onSubmit={handleSubmit}>送信</button>
          </form>
        </div>
      )}
    </div>
  );
}
