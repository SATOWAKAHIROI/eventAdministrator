import { AuthProvider } from "./context/AuthContext";
import { Route, BrowserRouter as Router, Routes, Navigate } from "react-router-dom";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Events from "./pages/Events";
import Notices from "./pages/Notices";
import Home from "./pages/Home";
import Overview from "./pages/Overview";
import UserProfile from "./pages/UserProfile";
import { ProtectedRoute } from "./common/ProtectedRoute";
import Layout from "./common/Layout";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />}></Route>
          <Route path="/login" element={<Login></Login>}></Route>
          <Route path="/signup" element={<Signup></Signup>}></Route>
          <Route
            path="/home"
            element={
              <ProtectedRoute>
                <Layout>
                  <Home></Home>
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/events"
            element={
              <ProtectedRoute>
                <Layout>
                  <Events></Events>
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/notices"
            element={
              <ProtectedRoute>
                <Layout>
                  <Notices></Notices>
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/overview"
            element={
              <ProtectedRoute>
                <Layout>
                  <Overview></Overview>
                </Layout>
              </ProtectedRoute>
            }
          ></Route>
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Layout>
                  <UserProfile></UserProfile>
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
