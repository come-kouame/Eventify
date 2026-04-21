"use client";

import {getHelloMessage} from "@/services/client";
import { useEffect, useState } from "react";

export function Hello() {
    const [message, setMessage] = useState<string>("");

    useEffect(() => {
        getHelloMessage()
            .then(setMessage)
            .catch(console.error);
    }, []);

    return <div>{message}</div>;
}