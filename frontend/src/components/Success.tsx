import axios from "axios"
import { useEffect, useState } from "react"
import { useSearchParams } from "react-router-dom";

export const Success = () => {
  const [message, setMessage] = useState<string | null>(null)

  const [searchParams] = useSearchParams();

  const accessToken = searchParams.get("access");
  const refreshToken = searchParams.get("refresh");

  useEffect(() => {
    if (accessToken) {
      axios
        .get("http://localhost:8080/test/admin", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((response) => {
          setMessage(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [accessToken]);  

  return (
    <div style={{ textAlign: "center", display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", height: "100vh", width: "100vw", gap: "1rem" }}>
      <h2>Success</h2>

      {message ? (
        <div style={{maxWidth: "30%", wordBreak: "break-all"}}>
          <p><strong>Message: </strong> {message} </p>
          <p><strong>Access token: </strong> {accessToken} </p>
          <p><strong>Refresh token: </strong> {refreshToken} </p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  )
}
