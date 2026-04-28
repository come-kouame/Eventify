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
    admin_id?: number | null;
}

export const EventModify = ({
                                      id,
                                      title,
                                      description,
                                      date,
                                      location,
                                      price = 0,
                                      image,
                                      place_init,
                                      place_current,
                                      admin_id,
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
                    {admin_id != null ? (
                        <a
                            href={`/event/${id}/participants`}
                            className="w-1/3 bg-blue-300 py-3 cursor-pointer hover:bg-blue-500 hover:text-white transition block"
                        >
                            Liste des participants
                        </a>
                    ) : (
                        <div className="w-1/3 bg-gray-300 py-3">
                            {location}
                        </div>
                    )}
                    <div
                        className={`w-1/3 py-3 flex items-center justify-center text-black ${
                            new Date(date) < new Date()
                                ? "bg-gray-400"
                                : admin_id == null
                                    ? "bg-orange-400"
                                    : "bg-green-400"
                        }`}
                    >
                        {new Date(date) < new Date()
                            ? "Passé"
                            : admin_id == null
                                ? "À valider"
                                : "Validé"}
                    </div>

                    <a href={`/event/modify/${id}`} className="w-1/3 bg-blue-300 py-3 cursor-pointer hover:bg-blue-500 hover:text-white transition block">
                        Modifier
                    </a>
                </div>
            </div>
        </div>
    );
};