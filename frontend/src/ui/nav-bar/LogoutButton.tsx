"use client";

export function LogoutButton() {
    const handleLogout = async () => {
        console.log("CLICK LOGOUT");
        alert("CLICK LOGOUT");

        await fetch("http://localhost:8080/auth/logout", {
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