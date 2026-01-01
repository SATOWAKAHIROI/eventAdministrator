import { AuthProvider } from "./context/AuthContext";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Events from "./pages/Events";
import Home from "./pages/Home";
import Notices from "./pages/Notices";
import Overview from "./pages/Overview";
import UserProfile from "./pages/UserProfile";
import Users from "./pages/Users";
import { ProtectedRoute } from "./common/ProtectedRoute";
import Layout from "./common/Layout";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route
            path="/home"
            element={
              <ProtectedRoute>
                <Layout>
                  <Home />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/events"
            element={
              <ProtectedRoute>
                <Layout>
                  <Events />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/notices"
            element={
              <ProtectedRoute>
                <Layout>
                  <Notices />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/overview"
            element={
              <ProtectedRoute>
                <Layout>
                  <Overview />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Layout>
                  <UserProfile />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/users"
            element={
              <ProtectedRoute>
                <Layout>
                  <Users />
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
