interface Props {
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

    return (
        <div className="flex w-full max-w-5xl bg-gray-200 rounded-lg overflow-hidden shadow">
            {/* Image */}
            <div className="w-1/6 bg-gray-300 flex items-center justify-center">
                <div className="w-full aspect-[210/297] overflow-hidden">
                    {image ? (
                        <img src={image} alt="event" className="object-cover w-full h-full" />
                    ) : (
                        <div className="flex items-center justify-center w-full h-full">
                            <p className="text-xl font-bold">Poster</p>
                        </div>
                    )}
                </div>
            </div>

            {/* Content */}
            <div className="w-5/6 flex flex-col justify-between">
                <div className="p-4">
                    <div className="flex justify-between items-start">
                        <div>
                            <h2 className="text-2xl font-bold">{title}</h2>
                            <p className="text-sm text-gray-700 ml-3">{date}</p>
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
                    <a href="/event/x" className="w-1/3 bg-gray-300 py-3 cursor-pointer hover:bg-gray-500 hover:text-white transition block">
                        Réserver
                    </a>
                </div>
            </div>
        </div>
    );
};