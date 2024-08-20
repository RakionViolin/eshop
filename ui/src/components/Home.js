const Home = ({user}) => {
    return (
      <>
        <h1>Home</h1>
        <p>Hello world<br/>
        How are you<br/> 
        </p>
        <img 
          className="oval"
          src = {user.imgURL}
          style={{
            width: user.imgSize,
            height: user.imgSize
          }}
          />
      </>
      
    );
  }

  export default Home;