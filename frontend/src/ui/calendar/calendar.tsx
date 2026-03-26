"use client";

export const Calendar = ({
    date,
    startDate,
    endDate,
    onSelectDate,
}: {
    date: Date;
    startDate: Date | null;
    endDate: Date | null;
    onSelectDate: (d: Date) => void;
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

    const isSameDay = (a: Date, b: Date) =>
        a.getFullYear() === b.getFullYear() &&
        a.getMonth() === b.getMonth() &&
        a.getDate() === b.getDate();

    const isInRange = (d: Date) => {
        if (!startDate || !endDate) return false;
        const t = d.setHours(0,0,0,0);
        const s = new Date(startDate).setHours(0,0,0,0);
        const e = new Date(endDate).setHours(0,0,0,0);
        return t >= Math.min(s, e) && t <= Math.max(s, e);
    };

    return (
        <div className="bg-white rounded-lg shadow p-4 w-64">
            <h3 className="text-lg font-bold capitalize mb-2">
                {monthName} {year}
            </h3>
            <div className="grid grid-cols-7 gap-1 text-center text-sm">
                {["L", "M", "M", "J", "V", "S", "D"].map((d, i) => (
                    <div key={i} className="font-semibold">{d}</div>
                ))}
                {days.map((d, i) => {
                    if (!d) return <div key={i}></div>;

                    const currentDate = new Date(year, month, d);

                    const isStart = startDate && isSameDay(currentDate, startDate);
                    const isEnd = endDate && isSameDay(currentDate, endDate);
                    const inRange = isInRange(new Date(currentDate));

                    return (
                        <div
                            key={i}
                            onClick={() => onSelectDate(currentDate)}
                            className={`py-1 cursor-pointer rounded
                ${isStart || isEnd ? "bg-blue-600 text-white" : ""}
                ${!isStart && !isEnd && inRange ? "bg-blue-200" : ""}
                ${!inRange && !isStart && !isEnd ? "hover:bg-gray-200" : ""}
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