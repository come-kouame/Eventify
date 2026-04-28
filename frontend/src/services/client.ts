"use client";

export async function getHelloMessage(): Promise<string> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/messages`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data = await res.json();
    return data[0].content;
}