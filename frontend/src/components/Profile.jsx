import React from "react";

function Profile({ artist }) {
  return (
    <div className="max-w-4xl mx-auto bg-white rounded-2xl shadow-md overflow-hidden">

      {/* Banner */}
      <div className="h-32 bg-linear-to-r from-indigo-500 to-purple-600"></div>

      {/* Profile Section */}
      <div className="px-8 pb-8">

        {/* Profile Picture */}
        <div className="flex justify-center">
          <div className="-mt-14">
            <img
              src={
                `https://static.vecteezy.com/system/resources/thumbnails/021/185/682/small/man-in-motocross-helmet-racer-rider-cyclist-concept-suitable-for-avatar-profiles-t-shirt-design-print-sticker-poster-vector.jpg`
              }
              alt={artist.name}
              className="w-28 h-28 rounded-full border-4 border-white object-cover shadow-md"
            />
          </div>
        </div>

        {/* Basic Info */}
        <div className="text-center mt-4">
          <h1 className="text-3xl font-bold text-gray-800">
            {artist.name}
          </h1>

          <p className="text-gray-500 mt-1">
            {artist.emailId}
          </p>
        </div>

        {/* Details */}
        <div className="grid md:grid-cols-2 gap-6 mt-8">

          <div className="bg-gray-50 p-4 rounded-xl">
            <p className="text-sm text-gray-500">
              Average Rating
            </p>

            <p className="text-2xl font-bold text-yellow-500">
              ⭐ {artist.rating?.toFixed(1) || 0}
            </p>
          </div>

          <div className="bg-gray-50 p-4 rounded-xl">
            <p className="text-sm text-gray-500">
              Total Ratings
            </p>

            <p className="text-2xl font-bold text-indigo-600">
              {artist.countOfRatings || 0}
            </p>
          </div>

          <div className="bg-gray-50 p-4 rounded-xl md:col-span-2">
            <p className="text-sm text-gray-500 mb-2">
              Genres
            </p>

            <div className="flex flex-wrap gap-2">
              {artist.genre?.map((g) => (
                <span
                  key={g}
                  className="px-3 py-1 bg-indigo-100 text-indigo-700 rounded-full text-sm"
                >
                  {g}
                </span>
              ))}
            </div>
          </div>

          <div className="bg-gray-50 p-4 rounded-xl md:col-span-2">
            <p className="text-sm text-gray-500">
              UPI ID
            </p>

            <input type="password" value="1234567890@axl" disabled/>
            
          </div>

        </div>

      </div>
    </div>
  );
}

export default Profile;