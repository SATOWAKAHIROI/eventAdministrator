import { useEffect, useState, type FormEvent } from "react";
import { useAuth } from "../../hooks/useAuth";
import { useNavigate, Link } from "react-router-dom";

type FormData = {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
};

export default function Signup() {
  const [formData, setFormData] = useState<FormData>({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const { register, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/home", { replace: true });
    }
  });

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (formData.password !== formData.confirmPassword) {
        setError("パスワードが一致しません");
        return;
      }

      const response = await register(
        formData.name,
        formData.email,
        formData.password
      );

      if (!response.success) {
        setError(response.error.message);
        return;
      }
    } catch (err: any) {
      if (err.response?.data?.error?.message) {
        setError(err.response.data.error.message);
      } else {
        setError("ユーザーの作成に失敗しました(サーバーエラー)");
      }
    } finally {
      setFormData({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
      });
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
      <h1>ユーザー作成</h1>
      <form onSubmit={handleSubmit}>
        <label>名前</label>
        <input
          id="name"
          name="name"
          type="text"
          required
          placeholder="山田 太郎"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
        />
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
        <label>確認用パスワード</label>
        <input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          required
          value={formData.confirmPassword}
          onChange={(e) =>
            setFormData({ ...formData, confirmPassword: e.target.value })
          }
        />
        <button type="submit" disabled={loading}>
          {loading ? "作成中" : "作成"}
        </button>
      </form>
      <Link to="/login">ログインはこちらから</Link>
    </div>
  );
}
