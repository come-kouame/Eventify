"use client";

export function LogoutButton() {
    const handleLogout = async () => {

        await fetch("http://192.168.75.28:8080/eventify/auth/logout", {
            method: "POST",
            credentials: "include",
        });

        window.location.href = "/login";
    };

    return (
        <button onClick={handleLogout}>
            Déconnexion
        </button>
    );
}