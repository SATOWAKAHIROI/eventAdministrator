import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function Header() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="bg-white shadow-sm border-b border-gray-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <Link to="/" className="flex items-center">
            <h1 className="text-xl font-bold text-gray-900">管理者ページ</h1>
          </Link>

          <div className="flex items-center gap-4">
            <Link
              to="/profile"
              className="text-sm text-gray-700 hover:text-indigo-600 transition-colors"
            >
              <span className="font-medium">{user?.name}</span>
              <span className="text-gray-500 ml-2">({user?.email})</span>
            </Link>
            <button
              onClick={handleLogout}
              className="px-4 py-2 text-sm bg-red-50 text-red-700 rounded-lg hover:bg-red-100 focus:outline-none focus:ring-2 focus:ring-red-500 transition-colors"
            >
              ログアウト
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}
