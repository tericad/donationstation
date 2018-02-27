function amountCorrection(amount){
    amount = amount.replace(/\$/g, '').replace(/\,/g, '');

    amount = parseFloat(amount);

    if (isNaN(amount)) {
      $('#error_explanation').html('<p>Please enter a valid amount in USD ($).</p>');
    }

    else {
      amount = amount * 100; // Needs to be an integer!
      amount = Math.round(amount);

    }
    return amount;
}

// Create a Stripe client.
var stripe = Stripe(stripeKey);
var donationAmount = amountCorrection(donation);

var paymentRequest = stripe.paymentRequest({
  country: 'US',
  currency: 'usd',
  total: {
    label: 'Donation Amount',
    amount: donationAmount,
  },
  requestPayerName: true,
  requestPayerEmail: true,
  requestShipping: true,
  // `shippingOptions` is optional at this point:
  shippingOptions: [
    // The first shipping option in this list appears as the default
    // option in the browser payment interface.
    {
    id: 'free-shipping',
    label: 'Tax letter will be mailed',
    //detail: 'Arrives in 4 to 8 weeks',
    amount: 0,
    },

  ],
});

var elements = stripe.elements();
var prButton = elements.create('paymentRequestButton', {
  paymentRequest: paymentRequest,
  style: {
    paymentRequestButton: {
      type: 'donate',
      theme: 'dark', // default: 'dark'
      height: '64px', // default: '40px', the width is always '100%'
    },
  },
});

// Check the availability of the Payment Request API first.
paymentRequest.canMakePayment().then(function(result) {
  if (result) {
    prButton.mount('#payment-request-button');
  } else {
    //document.getElementById('#payment-request-button').style.display = 'none';
  prButton.mount('#payment-request-button');
  }
});

$( "#amount" ).change(function() {
    var amount = $("#amount").val();
    donationAmount = amountCorrection(amount);
    paymentRequest.update({
        total: {
            label: 'Donation Amount',
            amount: donationAmount
            }
      })
});

paymentRequest.on('token', function(ev) {
  // Send the token to your server to charge it!
  fetch('/donate/charges', {
    method: 'POST',
      headers: {
        'Accept': 'application/json, text/plain, */*',
        'Content-Type': 'application/json'
      },
    body: JSON.stringify({token: ev.token.id,amount: donationAmount,shippingAddress: ev.shippingAddress,
        name: ev.payerName, email: ev.payerEmail}),
  })
  .then(function(response) {
    if (response.ok) {
      // Report to the browser that the payment was successful, prompting
      // it to close the browser payment interface.
      ev.complete('success');
      window.location="https://home.wasteoftime.org/donate/thanks";
    } else {
      // Report to the browser that the payment failed, prompting it to
      // re-show the payment interface, or show an error message and close
      // the payment interface.
      ev.complete('fail');
    }
  });
});

