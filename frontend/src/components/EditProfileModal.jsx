import React from 'react'


function EditProfileModal({
  artistDetail,
  setArtistDetail,
  handleEdit,
  onClose,
}) {
  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center">

      <form
        onSubmit={handleEdit}
        className="bg-white p-6 rounded-xl w-500px"
      >
        <h2 className="text-2xl font-bold mb-4">
          Edit Profile
        </h2>
        <label>Name:</label>
        <input
          type="text"
          value={artistDetail.name}
          onChange={(e) =>
            setArtistDetail({
              ...artistDetail,
              name: e.target.value,
            })
          }
          className="w-full border p-2 rounded mb-3"
        />

        <label>Email:</label>
        <input
          type="email"
          placeholder='If you channge the emailId, you must logout and login again to use the site 🙂'
          onChange={(e) =>
            setArtistDetail({
              ...artistDetail,
              emailId: e.target.value,
            })
          }
          className="w-full border p-2 rounded mb-3"
        />
        <label>UPI id:</label>
        <input
          type="text"
          value={artistDetail.upiId}
          onChange={(e) =>
            setArtistDetail({
              ...artistDetail,
              upiId: e.target.value,
            })
          }
          className="w-full border p-2 rounded mb-3"
        />
        <label>Genre:</label>
        <input
          type="text"
          value={artistDetail.genre.join(",")}
          onChange={(e) =>
            setArtistDetail({
              ...artistDetail,
              genre: e.target.value
                .split(",")
                .map((g) => g.trim()),
            })
          }
          className="w-full border p-2 rounded mb-4"
        />

        <div className="flex justify-end gap-3">
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 border rounded"
          >
            Cancel
          </button>

          <button
            type="submit"
            className="px-4 py-2 bg-indigo-600 text-white rounded"
          >
            Save
          </button>
        </div>
      </form>

    </div>
  );
}


export default EditProfileModal