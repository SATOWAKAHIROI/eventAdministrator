import { useEffect, useState, type FormEvent } from "react";
import { useAuth } from "../../hooks/useAuth";
import { useNavigate, Link } from "react-router-dom";

type FormData = {
  email: string;
  password: string;
};

export default function Login() {
  const [formData, setFormData] = useState<FormData>({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/home", { replace: true });
    }
  }, []);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await login(formData.email, formData.password);
      if (!response.success) {
        return;
      }
      // navigate("/home", { replace: true });
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

  return (
    <div>
      {error && (
        <div>
          <p>{error}</p>
        </div>
      )}
      <h1>ログインページ</h1>
      <form onSubmit={handleSubmit}>
        <label>メールアドレス</label>
        <input
          id="email"
          name="email"
          type="email"
          required
          placeholder="test@example.com"
          value={formData.email}
          onChange={(e) => setFormData({ ...formData, email: e.target.value })}
        />
        <label>パスワード</label>
        <input
          id="password"
          name="password"
          type="password"
          required
          value={formData.password}
          onChange={(e) =>
            setFormData({ ...formData, password: e.target.value })
          }
        />
        <button type="submit" disabled={loading}>
          {loading ? "ログイン中" : "ログイン"}
        </button>
      </form>
      <Link to="/signup">ユーザー作成はこちらから</Link>
    </div>
  );
}
