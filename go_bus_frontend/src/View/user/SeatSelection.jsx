import React, { useState } from 'react';
import './SeatSelection.css';
import { Link } from 'react-router-dom';

function SeatSelection() {
  const [selectedSeats, setSelectedSeats] = useState([]);

  const seatData = {
    lower: [
      [{ available: true, female: false }, { available: true, female: false }, { available: false, female: false }, { available: true, female: false }, { available: true, female: false }],
      [{ available: true, female: false }, { available: true, female: false }, { available: false, female: false }, { available: true, female: false }, { available: true, female: false }]
    ],
    upper: [
      [{ available: false, female: false }, { available: true, female: false }, { available: true, female: false }, { available: true, female: false }, { available: false, female: true }],
      [{ available: true, female: false }, { available: true, female: false }, { available: true, female: false }, { available: true, female: false }, { available: false, female: false }]
    ],
  };

  const handleSeatClick = (deckName, row, col) => {
    const seatKey = `${deckName}-${row}-${col}`;
    const isSelected = selectedSeats.includes(seatKey);

    if (seatData[deckName][row][col].available) {
      if (isSelected) {
        setSelectedSeats(selectedSeats.filter(seat => seat !== seatKey));
      } else {
        setSelectedSeats([...selectedSeats, seatKey]);
      }
    }
  };

  const renderSeats = (deckName, deckData) => (
    <div className="deck">
      {deckData.map((row, rowIndex) => (
        <div key={rowIndex} className="seat-row">
          {row.map((seat, colIndex) => {
            const seatKey = `${deckName}-${rowIndex}-${colIndex}`;
            const isSelected = selectedSeats.includes(seatKey);

            return (
              <div
                key={colIndex}
                className={`seat ${seat.available ? 'available' : 'unavailable'} ${seat.female ? 'female' : ''} ${isSelected ? 'selected' : ''}`}
                onClick={() => handleSeatClick(deckName, rowIndex, colIndex)}
              >
                {seat.available ? 'Π' : ''}
              </div>
            );
          })}
        </div>
      ))}
    </div>
  );

  return (
    <div className="container">
      <h1>Bus Seat Selection</h1>
      <p>Click on an Available seat to proceed with your transaction.</p>

      <div className="legend">
        <div className="seat available"></div> Available
        <div className="seat unavailable"></div> Unavailable
        <div className="seat female"></div> Female
      </div>

      <div className="bus-layout">
        <h2>Lower Deck</h2>
        {renderSeats('lower', seatData.lower)}

        <h2>Upper Deck</h2>
        {renderSeats('upper', seatData.upper)}
      </div>
<<<<<<< HEAD
<Link to="/Payment">
      <button className="proceed-button" disabled={selectedSeats.length === 0}>
        Proceed
      </button>
=======
      <Link to="/selectSeat">
        <button className="proceed-button" disabled={selectedSeats.length === 0}>
          Proceed
        </button>
>>>>>>> 71904f513c13a52d7b07e24e73c6f34616f151f7
      </Link>
    </div>
  );
}

export default SeatSelection;