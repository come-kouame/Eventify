"use client";

import React from "react";

export const Calendar = ({
    date,
    eventsDates = [],
    currentEventDate = null,
    selectedDate = null,
    onSelectDate,
    onMonthChange,
}: {
    date: Date;
    eventsDates?: Date[];
    currentEventDate?: Date | null;
    selectedDate?: Date | null;
    onSelectDate?: (date: Date) => void;
    onMonthChange?: (date: Date) => void;
}) => {
    const year = date.getFullYear();
    const month = date.getMonth();

    const jsDay = new Date(year, month, 1).getDay(); // 0 (Sun) - 6 (Sat)
    const firstDay = (jsDay + 6) % 7; // shift to Monday=0
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    const days = [];
    for (let i = 0; i < firstDay; i++) {
        days.push(null);
    }
    for (let i = 1; i <= daysInMonth; i++) {
        days.push(i);
    }

    const monthName = date.toLocaleString("fr-FR", { month: "long" });

    const prevMonth = () => {
        const newDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
        onMonthChange?.(newDate);
    };

    const nextMonth = () => {
        const newDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
        onMonthChange?.(newDate);
    };

    const isSameDay = (a: Date, b: Date) =>
        a.getFullYear() === b.getFullYear() &&
        a.getMonth() === b.getMonth() &&
        a.getDate() === b.getDate();


    const isToday = (d: Date) => {
        const today = new Date();
        return (
            d.getFullYear() === today.getFullYear() &&
            d.getMonth() === today.getMonth() &&
            d.getDate() === today.getDate()
        );
    };

    const isPast = (d: Date) => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return d < today;
    };

    const isEventDay = (d: Date) => {
        return eventsDates.some((e) =>
            e.getFullYear() === d.getFullYear() &&
            e.getMonth() === d.getMonth() &&
            e.getDate() === d.getDate()
        );
    };

    const isCurrentEvent = (d: Date) => {
        if (!currentEventDate) return false;
        return (
            d.getFullYear() === currentEventDate.getFullYear() &&
            d.getMonth() === currentEventDate.getMonth() &&
            d.getDate() === currentEventDate.getDate()
        );
    };

    const isSelected = (d: Date) => {
        if (!selectedDate) return false;
        return (
            d.getFullYear() === selectedDate.getFullYear() &&
            d.getMonth() === selectedDate.getMonth() &&
            d.getDate() === selectedDate.getDate()
        );
    };

    return (
        <div className="bg-white rounded-lg shadow p-4 w-64">
            <div className="flex items-center justify-between mb-2">
                <button
                    onClick={prevMonth}
                    className="px-2 py-1 rounded hover:bg-gray-200"
                >
                    ←
                </button>

                <h3 className="text-lg font-bold capitalize">
                    {monthName} {year}
                </h3>

                <button
                    onClick={nextMonth}
                    className="px-2 py-1 rounded hover:bg-gray-200"
                >
                    →
                </button>
            </div>
            <div className="grid grid-cols-7 gap-1 text-center text-sm">
                {["L", "M", "M", "J", "V", "S", "D"].map((d, i) => (
                    <div key={i} className="font-semibold">{d}</div>
                ))}
                {days.map((d, i) => {
                    if (!d) return <div key={i}></div>;

                    const currentDate = new Date(year, month, d);


                    return (
                        <div
                            key={i}
                            onClick={() => onSelectDate?.(currentDate)}
                            className={`py-1 cursor-pointer rounded
    ${isPast(currentDate)
        ? "text-gray-400 bg-gray-200 pointer-events-none"
        : isSelected(currentDate)
        ? "bg-black text-white"
        : isCurrentEvent(currentDate)
        ? "bg-green-500 text-white"
        : isToday(currentDate)
        ? "bg-blue-500 text-white"
        : isEventDay(currentDate)
        ? "bg-green-400 text-white"
        : "hover:bg-gray-200"
    }
`}
                        >
                            {d}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};