import type { ReactNode } from "react";
import { useAuth } from "../hooks/useAuth";
import { Navigate } from "react-router-dom"

export const ProtectedRoute = ({ children }: { children: ReactNode }) => {
    const { isAuthenticated, loading } = useAuth();

    if (loading) {
        return (
            <div>
                <div>読み込み中...</div>
            </div>
        )
    }

    if (!isAuthenticated) {
        return <Navigate to={"/login"} replace></Navigate>
    }

    return children
}