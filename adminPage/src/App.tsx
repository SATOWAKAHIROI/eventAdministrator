import { AuthProvider } from "./context/AuthContext";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Events from "./pages/Events";
import Home from "./pages/Home";
import Notices from "./pages/Notices";
import Overview from "./pages/Overview";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/home" element={<Home />}></Route>
          <Route path="/events" element={<Events />}></Route>
          <Route path="/notices" element={<Notices />}></Route>
          <Route path="/overview" element={<Overview />}></Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
