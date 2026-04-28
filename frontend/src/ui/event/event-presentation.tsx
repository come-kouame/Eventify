interface Props {
    id: number;
    title : string;
    description : string;
    date : string;
    location : string;
    price? : number;
    image : any;
    place_init : number;
    place_current : number;
}

export const EventPresentation = ({
    id,
    title,
    description,
    date,
    location,
    price = 0,
    image,
    place_init,
    place_current,
}: Props) => {
    let priceText = "";

    if (price <= 0) {
        priceText = "GRATUIT";
    } else {
        priceText = "À partir de " + price.toFixed(2) + "€";
    }

    const formattedDate = new Date(date).toLocaleString("fr-FR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        timeZone: "Europe/Paris",
    });

    return (
        <div className="flex w-full bg-gray-200 rounded-lg overflow-hidden shadow">

            {/* Content */}
            <div className="w-full flex flex-col justify-between">
                <div className="p-4">
                    <div className="flex justify-between items-start">
                        <div>
                            <h2 className="text-2xl font-bold">{title}</h2>
                            <p className="text-sm text-gray-700 ml-3">{formattedDate}</p>
                        </div>
                        <p className="text-sm font-semibold">{priceText}</p>
                    </div>

                    <p className="mt-4 text-sm text-gray-800">
                        <strong>Description de l’event :</strong> {description}
                    </p>
                </div>

                {/* Bottom bar */}
                <div className="flex text-center text-sm font-medium">
                    <div className="w-1/3 bg-gray-300 py-3">
                        {location}
                    </div>
                    <div className="w-1/3 bg-gray-400 py-3">
                        {place_current} places restantes
                    </div>
                    <a href={`/event/${id}`} className="w-1/3 bg-gray-300 py-3 cursor-pointer hover:bg-gray-500 hover:text-white transition block">
                        Réserver
                    </a>
                </div>
            </div>
        </div>
    );
};