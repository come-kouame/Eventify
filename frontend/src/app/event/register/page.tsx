"use client";

import { useSearchParams, useRouter } from "next/navigation";

export default function RegisterPage() {
    const searchParams = useSearchParams();
    const router = useRouter();

    const status = searchParams.get("status");

    return (
        <div className="min-h-screen flex flex-col items-center justify-center gap-8 bg-zinc-100">
            <h1 className="text-4xl font-bold text-center">
                {status === "200"
                    ? "Vous êtes bien inscrit ✅"
                    : "Vous êtes déjà inscrit ⚠️"}
            </h1>

            <button
                onClick={() => router.push("/")}
                className="bg-indigo-300 text-black text-2xl px-8 py-4 rounded-lg hover:bg-indigo-400"
            >
                Retour au menu
            </button>
        </div>
    );
}