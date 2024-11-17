import axios from "axios"
import { useEffect, useState } from "react"
import { useSearchParams } from "react-router-dom";

export const Success = () => {
  const [message, setMessage] = useState<string | null>(null)

  const [searchParams] = useSearchParams();

  const accessToken = searchParams.get("access");
  const refreshToken = searchParams.get("refresh");
  const error = searchParams.get("error");

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

  useEffect(() => {
    if (error) {
      setMessage(error);
    }
  }, [error]);
  

  return (
    <div>
      <h2>Success</h2>

      {message ? (
        <div>
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
