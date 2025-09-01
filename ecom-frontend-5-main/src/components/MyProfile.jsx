import React, { useEffect, useState } from "react";

const MyProfile = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Get user data from localStorage
    const storedUser = JSON.parse(localStorage.getItem("user"));
    if (storedUser) {
      // Exclude password
      const { password, ...userWithoutPassword } = storedUser;
      setUser(userWithoutPassword);
    }
  }, []);

  if (!user) return <div className="text-white p-4">Loading...</div>;

  return (
    <div className="profile-container">
  <h2 className="profile-title">Account Details</h2>
  <table className="profile-table">
    <tbody>
      <tr>
        <td className="profile-label">First Name:</td>
        <td className="profile-value">{user.firstName}</td>
      </tr>
      <tr>
        <td className="profile-label">Last Name:</td>
        <td className="profile-value">{user.lastName}</td>
      </tr>
      <tr>
        <td className="profile-label">Mobile Number:</td>
        <td className="profile-value">{user.mobileNumber}</td>
      </tr>
      <tr>
        <td className="profile-label">Email:</td>
        <td className="profile-value">{user.email || "N/A"}</td>
      </tr>
      <tr>
        <td className="profile-label">User ID:</td>
        <td className="profile-value">{user.user_id}</td>
      </tr>
      <tr>
        <td className="profile-label">Role:</td>
        <td className="profile-value">{user.role ? user.role.toUpperCase() : "N/A"}</td>
      </tr>
    </tbody>
  </table>
</div>
  );
};

export default MyProfile;