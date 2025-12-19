import { createContext, useEffect, useState, type ReactNode } from "react";
import adminUserService from "../service/adminUserService";

type User = {
  id: number;
  name: string;
  email: string;
  userRole: string;
};

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<any>;
  register: (name: string, email: string, password: string) => Promise<any>;
  logout: () => void;
  isAuthenticated: boolean;
  loading: boolean;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const currentUser = adminUserService.getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

  const login = async (email: string, password: string) => {
    const data = await adminUserService.login(email, password);
    setUser(data.data.user);
    return data;
  };

  const register = async (name: string, email: string, password: string) => {
    const data = await adminUserService.signup(name, email, password);
    return data;
  };

  const logout = () => {
    adminUserService.logout();
    setUser(null);
  };

  const value: AuthContextType = {
    user,
    login,
    register,
    logout,
    isAuthenticated: !!user,
    loading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
